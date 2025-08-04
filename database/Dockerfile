FROM mcr.microsoft.com/mssql/server:2019-latest

COPY ./BaseDatos.sql /tmp/BaseDatos.sql
COPY ./entrypoint.sh /tmp/entrypoint.sh

USER root

RUN sed -i 's/\r$//g' /tmp/entrypoint.sh

RUN chmod +x /tmp/entrypoint.sh

USER mssql

ENTRYPOINT ["/tmp/entrypoint.sh"]
