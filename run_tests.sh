#!/bin/bash

BASE_URL="https://raw.githubusercontent.com/PaBLOHenCh/ParserHtml/refs/heads/main/html_tests"

for i in $(seq -w 1 30); do
    echo "=============================="
    echo "Executando html_$i.html"
    echo "=============================="
    java HtmlAnalyser "$BASE_URL/html_$i.html"
    echo
done
