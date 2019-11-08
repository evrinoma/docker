## Description
How to run docker-compose
after install docker run
systemctl enable docker.service && systemctl start docker.service
To build production please run 
docker/scripts/prebuild
docker/scripts/build.prod

To deploying production please run, where {SERVICE} - service name for example 'sftp'
cp docker/compose/prod/{SERVICE}/docker.{SERVICE}.service /usr/lib/systemd/system/
systemctl enable docker.{SERVICE}.service
ln -s compose/prod/{SERVICE}/iptables.{SERVICE} iptables_up
ln -s compose/prod/{SERVICE}/docker-compose.{SERVICE}.yml docker-compose.yml
systemctl start docker.{SERVICE}.service
  
## Licence
This is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. See <http://www.gnu.org/licenses/>.

## Thanks

## Done