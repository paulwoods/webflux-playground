# Connection Strings

| database | string                                                    |
|----------|-----------------------------------------------------------|
| h2       | r2dbc:h2:mem:///userdb                                    |
| postgres | r2dbc:postgresql://localhost:5432/userdb                  |
| mysql    | r2dbc:mysql://localhost:3306/userdb                       |
| oracle   | r2dbc:oracle://?oracle.r2dbc.descriptor=(DESCRIPTION=...) |

# Spring Configuration

* spring.r2dbc.url=r2dbc:postgresql://localhost:5432/userdb
* spring.r2dbc.username=myuser
* spring.r2dbc.password=mypassword

# Links

* [r2dbc drivers](https://r2dbc.io/drivers/)
* [oracle r2dbc](https://github.com/oracle/oracle-r2dbc)

