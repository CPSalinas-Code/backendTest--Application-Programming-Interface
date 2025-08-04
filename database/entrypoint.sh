#!/bin/bash


/opt/mssql/bin/sqlservr &


echo "Esperamos 60 segundos hasta que inicie sql server..."
sleep 60


echo "Esperando que la base de datos este lista..."
until /opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "$SA_PASSWORD" -C -d "$MSSQL_DB" -Q "SELECT 1" > /dev/null 2>&1
do
  echo "Base de datos aun no esta lista... esperando"
  sleep 5
done

echo "Base de datos lista, iniciando script de creacion..."

/opt/mssql-tools18/bin/sqlcmd -S localhost -U sa -P "$SA_PASSWORD" -C -d "$MSSQL_DB" -i /tmp/BaseDatos.sql

echo "Configuracion finalizada


wait
