How to Release
==============

## Start a new release

        git flow release start RELEASE [BASE]

  For example,

        git flow release start 1.1 develop

# Set version

  For example,

        mvn org.codehaus.mojo:versions-maven-plugin:2.1:set -DgenerateBackupPoms=false -DnewVersion="1.1"

## Publish the release branch

        git flow release publish RELEASE

  For example,

        git flow release publish 1.1

## Finish the release

        git flow release finish RELEASE

  For example,

        git flow release finish 1.1

## Push ```master``` branch and bump up version in ```develop``` branch and push it.

  For example,

        git push origin master
        git checkout develop
        mvn org.codehaus.mojo:versions-maven-plugin:2.1:set -DgenerateBackupPoms=false -DnewVersion="1.2-SNAPSHOT"
        git push origin develop

# Push the newly generated tag.

  For example,

        git tag -l
        git push origin 1.1

## (Optional) Deploy the release

        mvn -Possrh-release deploy

