namespace cpp dsn.base
namespace java com.xiaomi.infra.pegasus.base
namespace py pypegasus.base

// place holder
struct blob
{
}

struct error_code
{
}

struct task_code
{
}

struct rpc_address
{
}

struct gpid
{
}

struct request_meta {
    1:i32 app_id;
    2:i32 partition_index;
    3:i32 client_timeout;
    4:i64 partition_hash;
}