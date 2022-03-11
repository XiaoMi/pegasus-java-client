include "base.thrift"

namespace cpp dsn.replication
namespace java com.xiaomi.infra.pegasus.replication

struct create_app_options
{
    1:i32              partition_count;
    2:i32              replica_count;
    3:bool             success_if_exist;
    4:string           app_type;
    5:bool             is_stateful;
    6:map<string, string>  envs;
}

struct configuration_create_app_request
{
    1:string                   app_name;
    2:create_app_options       options;
}

// meta server => client
struct configuration_create_app_response
{
    1:base.error_code  err;
    2:i32              appid;
}