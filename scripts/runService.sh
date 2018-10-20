#!/bin/bash
cd /var/website
java -jar target/personalwebsite.jar > /dev/null 2> /dev/null < /dev/null &
