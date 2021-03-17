package com.xiaomi.infra.pegasus.client;

import com.xiaomi.infra.pegasus.client.request.range.DeleteRange;
import com.xiaomi.infra.pegasus.client.request.range.GetRange;
import com.xiaomi.infra.pegasus.client.request.range.ScanResult;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeoutException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestRange {

  @Test // test for making sure return "maxFetchCount" if has "maxFetchCount" valid record
  public void testScanRangeWithValueExpired()
      throws PException, InterruptedException, TimeoutException, ExecutionException {
    String tableName = "temp";
    String hashKey = "hashKey";
    // generate records: sortKeys=[expired_0....expired_999,persistent_0...persistent_9]
    generateRecordsWithExpired(tableName, hashKey, 1000, 10);

    PegasusTableInterface table = PegasusClientFactory.getSingletonClient().openTable(tableName);
    GetRange getRange = new GetRange(table, hashKey.getBytes(), 0);
    // case A: scan all record
    // case A1: scan all record: if persistent record count >= maxFetchCount, it must return
    // maxFetchCount records
    ScanResult caseA1 = getRange.commitAndWait(5);
    assertScanResult(0, 4, false, caseA1);
    // case A2: scan all record: if persistent record count < maxFetchCount, it only return
    // persistent count records
    ScanResult caseA2 = getRange.commitAndWait(100);
    assertScanResult(0, 9, true, caseA2);

    // case B: scan limit record by "startSortKey" and "":
    // case B1: scan limit record by "expired_0" and "", if persistent record count >=
    // maxFetchCount, it must return maxFetchCount records
    ScanResult caseB1 = getRange.withStartSortKey("expired_0".getBytes()).commitAndWait(5);
    assertScanResult(0, 4, false, caseB1);
    // case B2: scan limit record by "expired_0" and "", if persistent record count < maxFetchCount,
    // it only return valid records
    ScanResult caseB2 = getRange.withStartSortKey("expired_0".getBytes()).commitAndWait(50);
    assertScanResult(0, 9, true, caseB2);
    // case B3: scan limit record by "persistent_5" and "", if following persistent record count <
    // maxFetchCount, it only return valid records
    ScanResult caseB3 = getRange.withStartSortKey("persistent_5".getBytes()).commitAndWait(50);
    assertScanResult(5, 9, true, caseB3);
    // case B4: scan limit record by "persistent_5" and "", if following persistent record count >
    // maxFetchCount, it only return valid records
    ScanResult caseB4 = getRange.withStartSortKey("persistent_5".getBytes()).commitAndWait(3);
    assertScanResult(5, 7, false, caseB4);

    // case C: scan limit record by "" and "stopSortKey":
    // case C1: scan limit record by "" and "expired_7", if will return 0 record
    getRange.withStartSortKey("".getBytes());
    ScanResult caseC1 = getRange.withStopSortKey("expired_7".getBytes()).commitAndWait(3);
    Assertions.assertTrue(caseC1.allFetched);
    Assertions.assertEquals(
        0, caseC1.results.size()); // among "" and "expired_7" has 0 valid record
    // case C2: scan limit record by "" and "persistent_7", if valid record count < maxFetchCount,
    // it only return valid record
    ScanResult caseC2 = getRange.withStopSortKey("persistent_7".getBytes()).commitAndWait(10);
    assertScanResult(0, 6, true, caseC2);
    // case C3: scan limit record by "" and "persistent_7", if valid record count > maxFetchCount,
    // it only return valid record
    ScanResult caseC3 = getRange.withStopSortKey("persistent_7".getBytes()).commitAndWait(2);
    assertScanResult(0, 1, false, caseC3);
  }

  @Test // test for making sure return "maxFetchCount" if has "maxFetchCount" valid record
  public void testDeleteRangeWithValueExpired()
      throws PException, InterruptedException, TimeoutException, ExecutionException {
    String tableName = "temp";
    String hashKey = "hashKey";
    // generate records: sortKeys=[expired_0....expired_999,persistent_0...persistent_9]
    generateRecordsWithExpired(tableName, hashKey, 1000, 10);
    PegasusTableInterface table = PegasusClientFactory.getSingletonClient().openTable(tableName);
    DeleteRange deleteRange = new DeleteRange(table, hashKey.getBytes(), 0);
    Boolean res1 = deleteRange.commitAndWait(5);
    Assertions.assertTrue(res1);

    Boolean res2 = deleteRange.commitAndWait(10);
    Assertions.assertTrue(res2);
    GetRange getRange = new GetRange(table, hashKey.getBytes(), 0);
    ScanResult getRes = getRange.commitAndWait(-1);
    Assertions.assertTrue(getRes.allFetched);
    Assertions.assertEquals(0, getRes.results.size());
  }

  private void generateRecordsWithExpired(
      String tableName, String hashKey, int expiredCount, int persistentCount)
      throws PException, InterruptedException {
    PegasusClientInterface client = PegasusClientFactory.getSingletonClient();
    // assign prefix to make sure the expire record is stored front of persistent
    String expiredSortKeyPrefix = "expired_";
    String persistentSortKeyPrefix = "persistent_";
    while (expiredCount-- > 0) {
      client.set(
          tableName,
          hashKey.getBytes(),
          (expiredSortKeyPrefix + expiredCount).getBytes(),
          (expiredSortKeyPrefix + expiredCount + "_value").getBytes(),
          1);
    }
    // sleep to make sure the record is expired
    Thread.sleep(1000);
    while (persistentCount-- > 0) {
      client.set(
          tableName,
          hashKey.getBytes(),
          (persistentSortKeyPrefix + persistentCount).getBytes(),
          (persistentSortKeyPrefix + persistentCount + "_value").getBytes());
    }
    PegasusClientFactory.closeSingletonClient();
  }

  private void assertScanResult(
      int startIndex, int stopIndex, boolean expectAllFetched, ScanResult actuallyRes) {
    Assertions.assertEquals(expectAllFetched, actuallyRes.allFetched);
    Assertions.assertEquals(stopIndex - startIndex + 1, actuallyRes.results.size());
    for (int i = startIndex; i <= stopIndex; i++) {
      Assertions.assertEquals(
          "hashKey", new String(actuallyRes.results.get(i - startIndex).getLeft().getKey()));
      Assertions.assertEquals(
          "persistent_" + i,
          new String(actuallyRes.results.get(i - startIndex).getLeft().getValue()));
      Assertions.assertEquals(
          "persistent_" + i + "_value",
          new String(actuallyRes.results.get(i - startIndex).getRight()));
    }
  }
}
