how to install

ssh root@172.18.2.1
create.user $dbpass

adbook
ssh root@172.18.72.5
init.install $dbpass

portal
ssh root@172.18.72.7
init.install $dbpass

pmc.ite-ng
ssh root@172.18.72.19
init.install

pmc.kzkt45
ssh root@172.18.72.20
init.install

gitblit
ssh root@172.18.1.10
init.install

1c
ssh root@172.18.83.150
public



