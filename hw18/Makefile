IMAGE       := postgres:16
CONTAINER   := otus-java-hw18
DB_NAME     := demoDB
DB_USER     := usr
DB_PASSWORD := pwd
HOST_PORT   := 5430
ROOT_DIR    := ..

.PHONY: up datastore client

up:
	docker pull $(IMAGE)
	docker run -d --rm --name $(CONTAINER) \
		-e POSTGRES_PASSWORD=$(DB_PASSWORD) \
		-e POSTGRES_USER=$(DB_USER) \
		-e POSTGRES_DB=$(DB_NAME) \
		-p $(HOST_PORT):5432 \
		$(IMAGE)

run-datastore:
	cd $(ROOT_DIR) && ./gradlew :hw18:datastore-service:bootRun

run-client:
	cd $(ROOT_DIR) && ./gradlew :hw18:client-service:bootRun
