[![Build Status](https://travis-ci.org/goetschalckx/spring-boot-logging-web.svg?branch=master)](https://travis-ci.org/goetschalckx/spring-boot-logging-web)
[![License](https://img.shields.io/github/license/goetschalckx/spring-boot-logging-web?color=4DC71F)](https://github.com/goetschalckx/spring-boot-logging-web/blob/master/LICENSE)

[![Coverage](https://codecov.io/gh/goetschalckx/spring-boot-logging-web/branch/master/graph/badge.svg)](https://codecov.io/gh/goetschalckx/spring-boot-logging-web)
[![Codacy](https://app.codacy.com/project/badge/Grade/39ea34a49b254b03bf84d5d1adbec00a)](https://www.codacy.com/gh/goetschalckx/spring-boot-logging-web?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=goetschalckx/spring-boot-logging-web&amp;utm_campaign=Badge_Grade)
[![Codacy Coverage](https://app.codacy.com/project/badge/Coverage/39ea34a49b254b03bf84d5d1adbec00a)](https://www.codacy.com/gh/goetschalckx/spring-boot-logging-web?utm_source=github.com&utm_medium=referral&utm_content=goetschalckx/spring-boot-logging-web&utm_campaign=Badge_Coverage)
[![CodeFactor](https://www.codefactor.io/repository/github/goetschalckx/spring-boot-logging-web/badge)](https://www.codefactor.io/repository/github/goetschalckx/spring-boot-logging-web)
[![Vulnerabilities](https://snyk.io/test/github/goetschalckx/spring-boot-logging-web/badge.svg)](https://snyk.io/test/github/goetschalckx/spring-boot-logging-web)

[![Release](https://img.shields.io/nexus/r/io.github.goetschalckx/spring-boot-logging-web?color=4DC71F&label=release&server=https%3A%2F%2Foss.sonatype.org%2F)](https://search.maven.org/artifact/io.github.goetschalckx/spring-boot-logging-web)
[![Snapshot](https://img.shields.io/nexus/s/io.github.goetschalckx/spring-boot-logging-web?label=snapshot&server=https%3A%2F%2Foss.sonatype.org%2F)](https://oss.sonatype.org/#nexus-search;quick~spring-boot-logging-web)

# spring-boot-logging-web
by Eric Goetschalckx

Provides automated web request and response logging for Spring (including advanced logback functionality)

## Minimum Requirements
Requires `spring-boot-starter-web` `1.4.0.RELEASE` or higher.
  
## Spring Configuration
`spring-boot-logging-web` includes request & response logging mechanisms for Spring `RestTemplate` and `RestController`.

These capabilities will have negative performance impact, so they are disabled by default. Including the body of the requests and responses in the log statements will further degrade performance.

The following configuration properties are available for managing logging settings:

```yaml
# Logging settings 
logging:
  
  # Spring Web request response logging settings
  web:
      
      # Spring RestTemplate response logging settings
      client:

        # RestTemplate request response bo logging enabled
        # Optional (default is false)
        enabled: true
     
        # Include web request response body in logs
        # Has performance impact due to byte stream copying
        # Optional (default is false)
        include-body: true

      # Spring RestTemplate response logging settings
      server:

        # RestTemplate request response bo logging enabled
        # Optional (default is false)
        enabled: true
     
        # Include web request response body in logs
        # Has performance impact due to byte stream copying
        # Optional (default is false)
        include-body: true
```

## Notes
Works well with `spring-boot-logging-json` quite nicely.
