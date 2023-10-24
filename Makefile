CI ?= false
BUILD_TYPE ?= Debug
GRADLE_ARGS ?= --build-cache

ifeq ($(CI), true)
  GRADLE_ARGS += --console 'plain'
  BUILD_TYPE = Release
endif

all: clean format lint test report assemble
.PHONY: all

assemble:
	./gradlew assemble${BUILD_TYPE} ${GRADLE_ARGS}
.PHONY: assemble

bundle:
	./gradlew bundle${BUILD_TYPE} ${GRADLE_ARGS}
.PHONY: bundle

clean:
	./gradlew clean ${GRADLE_ARGS}
.PHONY: clean

format:
	./gradlew formatKotlin ${GRADLE_ARGS}
.PHONY: format

lint:
	./gradlew lint${BUILD_TYPE} lintKotlin detekt ${GRADLE_ARGS}
.PHONY: lint

report:
	./gradlew koverHtmlReport koverXmlReport ${GRADLE_ARGS}
.PHONY: report

test:
	./gradlew test${BUILD_TYPE}UnitTest ${GRADLE_ARGS}
.PHONY: test
