.PHONY: run

run:
	@echo "cache file delete"
	@rm -rf ./axon-server-se/data/*
	@rm -rf ./axon-server-se/events/default/*
	@rm -rf ./db/data/*
	@echo "Running ./gradlew docker"
	@./gradlew docker
	@echo "Running docker-compose up -d"
	@docker-compose up -d