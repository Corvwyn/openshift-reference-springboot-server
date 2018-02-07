#!/usr/bin/env groovy

library identifier: 'aurora-pipeline-libraries@feature/AOS-1731', retriever: modernSCM(
            [$class: 'GitSCMSource',
             remote: 'https://git.aurora.skead.no/scm/ao/aurora-pipeline-libraries.git',
             credentialsId: 'aurora-bitbucket']) _

def version = 'feature/AOS-1731'
fileLoader.withGit('https://git.aurora.skead.no/scm/ao/aurora-pipeline-scripts.git', version) {
   jenkinsfile = fileLoader.load('templates/leveransepakke')
}

def systemtest = [
  auroraConfigEnvironment : 'st-refapp',
  path : 'src/systemtest',
  applicationUnderTest : "referanse",
  npmScripts : ['test'],
  gatling : [
    "appDir" : "gatling"
  ]
]

def overrides = [
  affiliation: "paas",
  testStages:[systemtest],
  piTests: false,
  credentialsId: "github"
  ]

jenkinsfile.run(version, overrides)


