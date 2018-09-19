include "base.thrift"

namespace cpp dsn.apps
namespace java com.xiaomi.infra.pegasus.apps
namespace py pypegasus.rrdb

// negotiation process:
//
//                       client               server
//                          | --- SASL_MECH --> |
//                          | <-- SASL_MECH --- |
//                          | - SASL_SEL_MECH ->|
//                          | <- SASL_SEL_OK ---|
//                          |                   |
//                          | --- SASL_INIT --> |
//                          |                   |
//                          | <-- SASL_CHAL --- |
//                          | --- SASL_RESP --> |
//                          |                   |
//                          |      .....        |
//                          |                   |
//                          | <-- SASL_CHAL --- |
//                          | --- SASL_RESP --> |
//                          |                   | (authentication will succeed
//                          |                   |  if all chanllenges passed)
//                          | <-- SASL_SUCC --- |
// (client won't response   |                   |
// if servers says ok)      |                   |
//                          | --- RPC_CALL ---> |
//                          | <-- RPC_RESP ---- |

enum negotiation_status
{
    INVALID = 0,
    SASL_LIST_MECHANISMS,
    SASL_LIST_MECHANISMS_RESP,
    SASL_SELECT_MECHANISMS,
    SASL_SELECT_MECHANISMS_OK,
    SASL_INITIATE,
    SASL_CHALLENGE,
    SASL_RESPONSE,
    SASL_SUCC,
    SASL_AUTH_FAIL
}

struct negotiation_message
{
    1: negotiation_status status;
    2: base.blob msg;
}

service security
{
    negotiation_message negotiate(1:negotiation_message nego_msg);
}
