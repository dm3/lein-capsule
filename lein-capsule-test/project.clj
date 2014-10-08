(defproject lein-capsule-test "0.1.0-SNAPSHOT"
  :description "FIXME: write description"

  :url "http://example.com/FIXME"

  :license {
    :name "Eclipse Public License"
    :url "http://www.eclipse.org/legal/epl-v10.html" }

  :plugins [
    [lein-capsule "0.1.0-SNAPSHOT"]
    [lein-pprint "1.1.1"] ]

  :dependencies [
    [org.clojure/clojure "1.6.0"] ]

  ;;; For test
  :profiles {
    :bla {
      :jvm-opts "-server" } }

  ;;; Capsule plugin configuration section, optional
  :capsule {

  ;;; Optional, defaults to "capsules" in project target dir, can't be overridden
    :output-dir "my-capsules"

  ;;; Optional
    ; TODO Implement plugin version check
    :min-plugin-version "0.1.0-SNAPSHOT"

  ;;; Optional, corresponds 1:1 to Log-Level manifest entry
    :log-level "info"

  ;;; Optional, defaults to <jarbasename>-<capsuletype>-capsule.jar, corresponds 1:1 to Log-Level manifest entry
    :name "capsule.jar"

  ;;; Optional, default behaviour is only :thin; if more than one type is configured, at least one must override :name
    :types {
      ;; Optional, can override anything
      :thin {}

      ;; Optional, can override anything, will trigger building a fat capsule
      :fat {
        :name "fat-capsule.jar"

        :execution {
          :runtime {
            :jdk-required true } } }

      ;; Optional, can override anything, will trigger building a mixed capsule
      :mixed [ {
        :name "first-and-only-mixed-capsule.jar"

        :execution {
          :runtime {
            :jdk-required true } }

        ;; Either :fat-ecept or :thin-except must be specified
        :fat-except [[org.clojure/clojure]] } ]

      ;; Optional, can override anything, shortcut for building a single capsule of type :fat-except Clojure's artifact
      :fat-except-clojure {
        :name "fat-without-clojure-capsule.jar" }

      ;; Optional, can override anything, shortcut for building a single capsule of type :thin-except Clojure's artifact
      :thin-except-clojure {
        :name "thin-with-clojure-capsule.jar" } }

  ;;; Optional, check https://github.com/puniverse/capsule#application-id for defaults
    :application {
      ;; Optional, corresponds 1:1 to Application-Name manifest entry, check https://github.com/puniverse/capsule#application-id for defaults
      :name "lein-capsule-test"
      ;; Optional, corresponds 1:1 to Application-Version manifest entry, check https://github.com/puniverse/capsule#application-id for defaults
      :version "0.1.0-SNAPSHOT" }

  ;;; Optional, capsule modes, each of them can override anything except types and application settings
    :profiles {
      :my-profile-1 {
        ;; Optional, this profile settings will override
        ;; top-level settings, defaults to false
        :default true

        :execution {
          :boot {
            :clojure-ns lein-capsule-test.core } } }

      :my-profile-2 {} }

  ;;; Optional
    :execution {
      ;; Optional
      :boot {
        ;; Optional, corresponds 1:1 to Main-Class manifest entry, default is "Capsule"
        :main-class "Capsule"

        ;; Optional, corresponds 1:1 to Extract-Capsule manifest entry, check https://github.com/puniverse/capsule#capsules-cache for defaults;
        ;; If the value here is not meaningful for the type of capsule to be built, it will be overridden automatically
        :extract-capsule false

        ;; Optional, if missing it'll be Leiningen's :main unless one of the following two is specified
        :clojure-ns main

        ;; Optional, applicable only if application-ns nor Leiningen's main; if neither is present then
        ;; the next one must be
        :scripts {
          ;; Mandatory, corresponds 1:1 to Unix-Script manifest entry
          :unix ""
          ;; Mandatory, corresponds 1:1 to Windows-Script manifest entry
          :windows "" }

        ;; Optional, applicable only if none of the above is; in that case, if missing, the project's
        ;; complete artifact ID will be used; corrisponds 1:1 to Application manifest entry
        :artifact [my/artifact "1.0.0"]

        ;; Optional, program arguments, defaults to none
        :args [] }

      ;; Optional, check https://github.com/puniverse/capsule#selecting-the-java-runtime for defaults
      :runtime {
        ;; Optional, corrisponds 1:1 to Java-Version manifest entry
        :java-version ""
        ;; Optional, corrisponds 1:1 to Min-Java-Version manifest entry
        :min-java-version ""
        ;; Optional, corrisponds 1:1 to Min-Update-Version manifest entry
        :min-update-version ""
        ;; Optional, corrisponds 1:1 to JDK-Required manifest entry
        :jdk-required false

        ;; Optional, corrisponds 1:1 to JVM-Args manifest entry
        :jvm-args []

        ;; Optional, corrisponds 1:1 to System-Properties manifest entry
        :system-properties {}

        ;; Optional, corrisponds 1:1 to Environment-Variables manifest entry
        :environment-variables {}

        ;; Optional, corresponds 1:1 to Java-Agents manifest entry
        :agents [
          ;; Optional
          [:embedded {
            ;; Mandatory
            :jar ""
            ;; Optional
            :params [] } ]
          [:artifact {
            ;; Mandatory
            :id ""
            ;; Optional
            :params [] } ] ]

        :paths {
          ;; Optional, corresponds 1:1 to App-Class-Path manifest entry
          :app-clas-spath []

          ;; Optional
          :boot-class-path {
            ;; Optional, corresponds 1:1 to Boot-Class-Path-P manifest entry
            :prepend []
            ;; Optional, corresponds 1:1 to Boot-Class-Path manifest entry
            :override []
            ;; Optional, corresponds 1:1 to Boot-Class-Path-A manifest entry
            :append [] }

          ;; Optional
          :native-library-path {
            ;; Optional, corresponds 1:1 to Library-Class-Path-P manifest entry
            :prepend []
            ;; Optional, corresponds 1:1 to Library-Class-Path-A manifest entry
            :append [] } }

        :security {
          ;; Optional, corresponds to Security-Manager
          :manager ""
          ;; Optional
          :policy {
            ;; Optional, corresponds to Security-Policy-A manifest entry
            :append ""
            ;; Optional, corresponds to Security-Policy manifest entry
            :override "" } } } }

  ;;; Optional, check https://github.com/puniverse/capsule#maven-dependencies for defaults
    :maven-dependencies {
      ;; Optional, corresponds to Allow-Snapshots manifest entry
      :allow-snapshots false

      ;; Optional, corresponds to Repositories manifest entry
      :repositories [central]

      ;; Optional
      :artifacts {
        ;; Optional
        :jvm {
          ;; Optional, array of Leinigen coordinates to remove
          :remove []
          ;; Optional, array of Leinigen dependencies
          :add [] }
        ;; Optional
        :native {
          ;; Optional
          :windows []
          ;; Optional
          :mac []
          ;; Optional
          :linux [] } } } } )