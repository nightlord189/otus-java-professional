IMAGE       := postgres:13
CONTAINER   := otus-java-hw10
DB_NAME     := demoDB
DB_USER     := usr
DB_PASSWORD := pwd
HOST_PORT   := 5430
SCHEMA      := src/main/resources/db/migration/V1__initial_schema.sql

.PHONY: up migrate

up:
	docker pull $(IMAGE)
	docker run -d --rm --name $(CONTAINER) \
		-e POSTGRES_PASSWORD=$(DB_PASSWORD) \
		-e POSTGRES_USER=$(DB_USER) \
		-e POSTGRES_DB=$(DB_NAME) \
		-p $(HOST_PORT):5432 \
		$(IMAGE)


migrate:
	docker exec -i $(CONTAINER) psql -U $(DB_USER) -d $(DB_NAME) < $(SCHEMA)
	@echo "migration applied"
