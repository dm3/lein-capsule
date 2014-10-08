; TODO Review for compliance with https://github.com/bbatsov/clojure-style-guide

(ns ^{ :author "circlespainter" :internal true } leiningen.capsule
  "Main entry for lein-capsule plugin"

  (:require
    [leiningen.core.project :as project]

    [leiningen.jar :as jar]
    [leiningen.clean :as clean]

    [leiningen.capsule.utils :as cutils]
    [leiningen.capsule.build :as cbuild]
    [leiningen.capsule.spec :as cspec]))

(defn capsule
  "Creates specified capsules for the project"
  [project & args]

  (let [default-profile (cutils/get-capsule-default-profile project)
        project (cspec/validate-and-normalize project)
        jar-files (jar/jar project)]
    (doseq [[capsule-type-name v] (cutils/get-capsule-types project)]
      (let [project (cutils/get-project-without-default-profile project)
            project (project/merge-profiles project [{:capsule default-profile} {:capsule v}])
            project (cspec/capsulize project)]
        (cbuild/build-capsule jar-files project capsule-type-name v)))))