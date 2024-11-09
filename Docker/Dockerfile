FROM ubuntu:latest
LABEL Author="Celeghin"
LABEL Project="Barista"
ENV DEBIAN_FRONTEND=noninteractive
RUN apt update && apt install git unzip git apache2 -y
CMD [ "/usr/sbin/apache2ctl", "-D", "FOREGROUND" ]
EXPOSE 80
WORKDIR /var/www/html
VOLUME [ "/var/log/apache2" ]
ADD barista.tar.gz /var/www/html
