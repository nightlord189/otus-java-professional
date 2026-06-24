IMAGE       := postgres:13
CONTAINER   := otus-java-hw10
DB_NAME     := demoDB
DB_USER     := usr
DB_PASSWORD := pwd
HOST_PORT   := 5430

.PHONY: up

up:
	docker pull $(IMAGE)
	docker run -d --rm --name $(CONTAINER) \
		-e POSTGRES_PASSWORD=$(DB_PASSWORD) \
		-e POSTGRES_USER=$(DB_USER) \
		-e POSTGRES_DB=$(DB_NAME) \
		-p $(HOST_PORT):5432 \
		$(IMAGE)
