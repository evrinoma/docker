#remote debug
ssh -R 9000:localhost:9000 root@php72.freepbx 

php -dxdebug.remote_enable=1 -dxdebug.remote_mode=req -dxdebug.remote_port=9000 -dxdebug.remote_host=172.20.1.78 -dxdebug.remote_autostart=1  -dxdebug.remote_connect_back=0 /opt/WWW/container.ite-ng.ru/projects/httpd/freepbx/install


