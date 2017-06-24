#!/bin/bash

WEBSITE_PING="https://cjhawley.com/ping"

response_code=$(curl -w %{http_code} -s -o /dev/null ${WEBSITE_PING})

[[ "$response_code" == "200" ]]
