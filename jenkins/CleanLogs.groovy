node {
    def numberOfBuildsToKeep = MAX_BUILDS as Integer
    def projects = Jenkins.instance.getAllItems(hudson.model.Job.class);
    projects.each {
        if( it.class.toString() == "class org.jenkinsci.plugins.workflow.job.WorkflowJob" ) {
            if (it.getName() == "clear") {
                println it.getClass()
                println it.getName()
                def    builds = it.getBuilds()
                println builds.size()
                def size = (builds.size()-1)
                println "size " + size
                for(int i = size ; i > numberOfBuildsToKeep; i--) {
                    def item = builds.get(i)
                    println "item [" + item+"] i ["+i+"]"
                    item.delete()
                }
            }
        }
    }
}