# spring-boot-starter-web-logging

Provides automated web request and response logging for Spring (including advanced logback functionality)

## Minimum Requirements
Requires `spring-boot-starter-web` `1.4.0.RELEASE'` or higher.
  
## Spring Configuration
`spring-boot-starter-web-logging` includes request & response logging mechanisms for Spring `RestTemplate` and `RestController`.

These capabilities can have negative performance impact, so they are disabled by default.

The following configuration properties are available for managing logging settings:

```yaml
# Logging settings 
logging:
  
  # Spring Web request response logging settings
  web:
  
     # Web request response logging enabled
     # Optional (default is false)
     enabled: true
     
     # Include web request response body in logs
     # Has performance impact due to byte stream copying
     # Optional (default is false)
     include-body: true
```
