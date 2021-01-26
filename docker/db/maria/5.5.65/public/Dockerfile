FROM \
		evrinoma/db.maria:latest

MAINTAINER \
		evrinoma@gmail.com

LABEL \
		name="evrinoma Web Maria SQL Image" \
		image="maria.db.public" \
		vendor="evrinoma@gmail.com" \
		license="MIT" \
		build-date="2020-03-02"

RUN echo '#!/bin/bash' > /usr/local/bin/init.container && \
    echo 'sleep 10 && create.user' >> /usr/local/bin/init.container
