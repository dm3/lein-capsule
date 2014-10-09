(ns ^{ :author "circlespainter" :internal true } leiningen.capsule.consts)

(def ^:internal capsule-version "0.10.0-SNAPSHOT")

(def ^:internal clojars-repo-url "http://clojars.org/repo")

;; TODO Restore it to :manifest and maybe try reusing some uberjar logic when https://github.com/technomancy/leiningen/pull/1700 is merged
;; In the meanwhile going on with alternative impl.
(def ^:internal kwd-capsule-manifest :capsule-manifest)

(def ^:internal path-main [:main])

(def ^:internal path-output-dir [:output-dir])
(def ^:internal path-log-level [:log-level])
(def ^:internal path-application-name [:application :name])
(def ^:internal path-application-version [:application :version])
(def ^:internal path-capsule-default-name [:name])
(def ^:internal path-execution-boot-main-class [:execution :boot :main-class])
(def ^:internal path-execution-boot-clojure-ns [:execution :boot :clojure-ns])
(def ^:internal path-execution-boot-scriptsx [:execution :boot :scripts])
(def ^:internal path-execution-boot-artifact [:execution :boot :artifact])
(def ^:internal path-execution-boot-extract-capsule [:execution :boot :extract-capsule])
(def ^:internal path-execution-boot-args [:execution :boot :args])
(def ^:internal path-execution-runtime [:execution :runtime])
(def ^:internal path-runtime-java-version [:runtime :java-version])
(def ^:internal path-runtime-min-java-version [:runtime :min-java-version])
(def ^:internal path-runtime-min-update-version [:runtime :min-update-version])
(def ^:internal path-runtime-jdk-required [:runtime :jdk-required])
(def ^:internal path-runtime-jvm-args [:runtime :jvm-args])
(def ^:internal path-runtime-system-properties [:runtime :system-properties])
(def ^:internal path-runtime-agents [:runtime :agents])
(def ^:internal path-runtime-app-class-path [:runtime :paths :app-class-path])
(def ^:internal path-runtime-boot-class-path-p [:runtime :paths :boot-class-path :prepend])
(def ^:internal path-runtime-boot-class-path-a [:runtime :paths :boot-class-path :append])
(def ^:internal path-runtime-boot-class-path [:runtime :paths :boot-class-path :override])
(def ^:internal path-runtime-native-library-path-p [:runtime :paths :native-library-path :prepend])
(def ^:internal path-runtime-native-library-path-a [:runtime :paths :native-library-path :append])
(def ^:internal path-runtime-security-manager [:runtime :security :manager])
(def ^:internal path-runtime-security-policy-a [:runtime :security :policy :append])
(def ^:internal path-runtime-security-policy [:runtime :security :policy :override])
(def ^:internal path-maven-dependencies-allow-snapshots [:maven-dependencies :allow-snapshots])
(def ^:internal path-maven-dependencies-repositories [:maven-dependencies :repositories])
(def ^:internal path-maven-dependencies-artifacts-jvm-remove [:maven-dependencies :artifacts :jvm :remove])
(def ^:internal path-maven-dependencies-artifacts-jvm-add [:maven-dependencies :artifacts :jvm :add])
(def ^:internal path-maven-dependencies-artifacts-native-mac [:maven-dependencies :artifacts :native :mac])
(def ^:internal path-maven-dependencies-artifacts-native-linux [:maven-dependencies :artifacts :native :linux])
(def ^:internal path-maven-dependencies-artifacts-native-windows [:maven-dependencies :artifacts :native :windows])

(def ^:internal path-modes [:capsule :modes])
(def ^:internal path-types [:capsule :types])

(defn- non-default-mode [project & [mode-keyword]]
  "Determines if the mode is a default one"
  (and mode-keyword (not (get-in project (concat path-modes [mode-keyword :default])))))

(defn ^:internal mode-aware-path [project path & [mode-keyword]]
  "Returns the correct path in the project's capsule specification sub-map (source) based on path, mode name and
  default setting"
  (if (non-default-mode project mode-keyword)
    (concat (concat path-modes [mode-keyword]) path)
    (cons :capsule path)))

(defn ^:internal capsule-manifest-path [project & [mode-keyword]]
  "Returns the correct path in the project's manifest sub-map (target) based on mode name and default setting"
  (if (non-default-mode project mode-keyword)
    [kwd-capsule-manifest mode-keyword]
    [kwd-capsule-manifest]))