#!/bin/bash

SERVICE="http://localhost:8080"
CURL="curl -s --no-keepalive"

s_create=$($CURL -i -X POST $SERVICE/session/create)

s_loc=$(grep "location:" <<< $s_create | tr -d '\r' | cut -d " " -f 2)

session=$($CURL "${SERVICE}${s_loc}")

token=$(jq -r .session.token <<< "$session")

$CURL -X POST --data '{ "url": "http://localhost:8080/cal/123", "label": "test" }' \
              -H "Content-type: application/json" \
              "${SERVICE}/session/${token}/source"

$CURL -X POST --data '{ "url": "http://localhost:8080/cal/354", "label": "test2" }' \
              -H "Content-type: application/json" \
              "${SERVICE}/session/${token}/source"

jq <<< "$($CURL "${SERVICE}${s_loc}")"
