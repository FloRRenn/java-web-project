FROM nginx:latest

ENV TZ=Asia/Ho_Chi_Minh
RUN ln -snf /usr/share/zoneinfo/$TZ /etc/localtime
RUN echo $TZ > /etc/timezone

COPY ./fe-src/ /var/www/html/

RUN rm -rf /etc/nginx/conf.d/default.conf

COPY ./nginx/ /etc/nginx/

# RUN chown -R root:www-data /var/www/html/
# RUN chmod 750 /var/www/html/
# RUN find . -type f -exec chmod 640 {} \;
# RUN find . -type d -exec chmod 750 {} \;

# RUN chmod +t -R /var/www/html/