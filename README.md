## Description
Folder - Docker/scripts
- flush - This script flush all iptables records, restarting docker service and remove all docker exited process.
- prebuild - This script try to assemble the main images.
- build - This script try to build dependency images. Some images cannot to be builded without main images.
- build.dev - Build and run develope version.
- build.prod - Build and run production version.
- restart -  This script is restarting docker mashine.
- restart.dev - restart develope version.
- restart.prod - restart production version.
- stop - This script is stop docker mashine.
- stop.dev - stop develope version.
- stop.prod - stop production version.

- hosts - This script register network names.
- iptables - Forward external IP on local IPs.
- permissions - 

## AutoDeploy
PHP images only for developer mode

    environment:
        - ENGINE=httpd - httpd or nginx
        - DEPLOY=yes - enable mode
        - MODE=dev - remove xdebug if value prod
        - NODEJS= - version nodejs
        - web_conf=/opt/WWW/container.ite-ng.ru/projects/httpd/cont.api/vhost.conf - path to config
        - web_dir=/opt/WWW/container.ite-ng.ru/projects/httpd/cont.api - path to folder

or

    environment:
        - ENGINE=httpd - httpd or nginx
        - DEPLOY=yes - enable mode
        - SYMFONY=yes - only for symfony app
        - MODE=dev - remove xdebug if value prod
        - NODEJS= - version nodejs
        - web_dir=/opt/WWW/container.ite-ng.ru/projects/httpd/cont.api/public - path to public folder
        - web_server=my.dns.name - server name you could set it in /etc/hosts
        - web_alias=alias - short server name                        
        
## Licence
This is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. See <http://www.gnu.org/licenses/>.

## Thanks


## Done
