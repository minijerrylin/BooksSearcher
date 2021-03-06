node {
    def project = 'my-tw-zone-project'
    def appName = 'booksearcher'
    def imageTag = "gcr.io/${project}/${appName}:${env.BRANCH_NAME}.${env.BUILD_NUMBER}"
    def serviceCanary = "${appName}-canary"
    def serviceProd = "${appName}-prod"

    checkout scm

    stage 'Testing'
    sh("./mvnw test")

    stage 'Packaging'
    sh("./mvnw -DskipTests package")

    stage 'Build image'
    sh("docker build -t ${imageTag} .")

    stage 'Push image to registry'
    sh("gcloud docker -- push ${imageTag}")

    stage "Deploy Application"
    switch (env.BRANCH_NAME) {
    // Roll out to canary environment
        case "canary":
            // Change deployed image in canary to the one we just built
            sh("sed -i.bak 's#booksearcher:1.0.0#${imageTag}#' ./k8s/canary/*.yaml")
            sh("kubectl --namespace=production apply -f k8s/canary/")
            sh("echo http://`kubectl --namespace=production get service/${serviceCanary} --output=json | jq -r '.status.loadBalancer.ingress[0].ip'` > ${serviceCanary}")
            break

    // Roll out to production
        case "master":
            // Change deployed image in canary to the one we just built
            sh("sed -i.bak 's#booksearcher:1.0.0#${imageTag}#' ./k8s/production/*.yaml")
            sh("kubectl --namespace=production apply -f k8s/production/")
            sh("echo http://`kubectl --namespace=production get service/${serviceProd} --output=json | jq -r '.status.loadBalancer.ingress[0].ip'` > ${serviceProd}")
            break

    // Roll out a dev environment
        default:
            // Create namespace if it doesn't exist
            sh("kubectl get ns ${env.BRANCH_NAME} || kubectl create ns ${env.BRANCH_NAME}")
            // Don't use public load balancing for development branches
            sh("sed -i.bak 's#bbooksearcher:1.0.0#${imageTag}#' ./*.yaml")
            sh("kubectl --namespace=${env.BRANCH_NAME} apply -f k8s/dev/")
            echo 'To access your environment run `kubectl proxy`'
            echo "Then access your service via http://localhost:8001/api/v1/proxy/namespaces/${env.BRANCH_NAME}/services/${serviceNmae}:80/"
    }
}