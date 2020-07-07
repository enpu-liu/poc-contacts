## Build

### Docker Build

Use `sbt docker:publishLocal` for docker build. 

### Configuration

The following attributes can be configured via command line as system environment.
`CI_DASHBOARD_MONGODB_URI`, default value: mongodb://127.0.0.1:27017/vad
`CI_DASHBOARD_LOG_ROOT_LEVEL`, default value: INFO
`CI_DASHBOARD_LOG_APPENDER`, default value: STDOUT

### Deployed URL
