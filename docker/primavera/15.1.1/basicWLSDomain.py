#=======================================================================================
# This is an example of a simple WLST offline configuration script. The script creates 
# a simple WebLogic domain using the Basic WebLogic Server Domain template. The script 
# demonstrates how to open a domain template, create and edit configuration objects, 
# and write the domain configuration information to the specified directory.
#
# This sample uses the demo Derby Server that is installed with your product.
# Before starting the Administration Server, you should start the demo Derby server
# by issuing one of the following commands:
#
# Windows: WL_HOME\common\derby\bin\startNetworkServer.cmd
# UNIX: WL_HOME/common/derby/bin/startNetworkServer.sh
#
# (WL_HOME refers to the top-level installation directory for WebLogic Server.)
#
# The sample consists of a single server, representing a typical development environment. 
# This type of configuration is not recommended for production environments.
#
# Please note that some of the values used in this script are subject to change based on 
# your WebLogic installation and the template you are using.
#
# Usage: 
#      java weblogic.WLST <WLST_script> 
#
# Where: 
#      <WLST_script> specifies the full path to the WLST script.
#=======================================================================================

#=======================================================================================
# Open a domain template.
#=======================================================================================

readTemplate("###WEBLOGIC_SRW/wlserver/common/templates/wls/wls.jar")

#=======================================================================================
# Configure the Administration Server and SSL port.
#
# To enable access by both local and remote processes, you should not set the 
# listen address for the server instance (that is, it should be left blank or not set). 
# In this case, the server instance will determine the address of the machine and 
# listen on it. 
#=======================================================================================

cd('Servers/AdminServer')
set('ListenAddress','')
set('ListenPort', 7001)

create('AdminServer','SSL')
cd('SSL/AdminServer')
set('Enabled', 'True')
set('ListenPort', 7002)

#=======================================================================================
# Define the user password for weblogic.
#=======================================================================================

cd('/')
cd('Security/base_domain/User/weblogic')
#cd('Security/base_domain/User/###PRIMAVERA_USERNAME')
# Please set password here before using this script, e.g. cmo.setPassword('value')
cmo.setPassword('###PRIMAVERA_PASSWORD')

#=======================================================================================
# Write the domain and close the domain template.
#=======================================================================================

setOption('OverwriteDomain', 'true')
writeDomain('###DOMAIN_HOME')
closeTemplate()

#=======================================================================================
# Exit WLST.
#=======================================================================================

exit()
