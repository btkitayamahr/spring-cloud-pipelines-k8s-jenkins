import javaposse.jobdsl.dsl.DslFactory

DslFactory factory = this

factory.job('msa-pipeline-seed') {
	scm {
		git('${TOOLS_REPOSITORY}', '${TOOLS_BRANCH}')
	}
	wrappers {
		parameters {
			stringParam('TOOLS_REPOSITORY', 'https://github.com/btkitayamahr/spring-cloud-pipelines.git', "The repository with pipeline functions")
			stringParam('TOOLS_BRANCH', 'develop', "The branch with pipeline functions")
		}
	}
	steps {
		gradle("clean build -x test")
		dsl {
			external('jenkins/jobs/jenkins_pipeline_jenkinsfile_msa.groovy')
			removeAction('DISABLE')
			removeViewAction('DELETE')
			ignoreExisting(false)
			lookupStrategy('SEED_JOB')
			additionalClasspath([
				'jenkins/src/main/groovy', 'jenkins/src/main/resources', 'jenkins/build/lib/*.*'
			].join("\n"))
		}
	}
}
