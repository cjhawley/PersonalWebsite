#!/bin/bash
pkill -f personalwebsite
mv /var/website website.$(date +%Y%m%d%H%M%S).bak
