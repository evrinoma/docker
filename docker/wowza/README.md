## Description
How to install, please make a folder next structure
mkdir -p ./versions/X.Y.Z/install
where X.Y.Z - is a version of wowza
download from official site [a link](https://www.wowza.com/pricing/installer)
wowza 64 -bit installer with silent mode support
WowzaStreamingEngine-X.Y.Z-linux-x64-installer.run
create file in ./versions/X.Y.Z/install/wowza by command 
touch ./versions/X.Y.Z/install/wowza
with next environment
WOWZA_LICENSE=
WOWZA_LICENSE_SILENT=
WOWZA_USERNAME=
WOWZA_PASSWORD=
WOWZA_INSTALL_PATH="/usr/local/"

## Licence
This is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version. See <http://www.gnu.org/licenses/>.

## Thanks

## Done