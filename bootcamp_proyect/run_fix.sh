#!/bin/bash
# Script para ejecutar el SQL directamente desde bash
# Requiere tener sqlcmd instalado

sqlcmd -S nodossolutions.com,1435 -U talento -P cartagena -d DB_Intership_mangement_JPA -i fix_interns_nullable.sql
