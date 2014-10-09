(ns ^{ :author "circlespainter" :internal true } leiningen.capsule.utils
  "Various capsule-building utilities"
  (:require [leiningen.capsule.consts :as cc]))

(defn ^:internal get-capsule-types [project]
  "Extracts, normalizes and returns the enabled capsule types sub-map, if any"
  (mapcat
    #(let [[k v] %] (if (sequential? v) (map (fn [e] [k e]) v) [%]))
    (seq (get-in project cc/path-types))))

(defn ^:internal get-capsule-default-mode [project]
  "Extracts and returns the default capsule mode sub-map, if any"
  (reduce-kv (fn [i _ v] (merge i (if (:default v) (dissoc v :default) {}))) {} (get-in project cc/path-modes)))

(defn ^:internal get-capsule-default-mode-name [project]
  "Extracts and returns the default capsule mode sub-map, if any"
  (reduce-kv (fn [i k v] (str i (if (:default v) k ""))) "" (get-in project cc/path-modes)))

(defn ^:internal get-project-without-default-mode [project]
  "Removes the default capsule mode from project"
  (update-in project cc/path-modes
    #(reduce-kv (fn [modes-map k v] (if (:default v) (dissoc modes-map k) modes-map)) %1 %1)))

(defn ^:internal artifact-to-string [[sym ver]]
  "Returns the string representation of a Leiningen artifact"
  (let [nmspc (namespace sym)
        nmsym (name sym)]
    (str
      (if nmspc (str nmspc ":") "")
      nmsym ":" ver)))

(defn ^:internal capsule-manifest [project]
  "Extracts the capsule-based manifest sub-map from a Leiningen project"
  (cc/kwd-capsule-manifest project))

(defn ^:internal add-to-manifest-if-path [project path manifest-entry-name f & [mode-keyword]]
  "Will add an entry's transformation through \"f\" in the given non-mode-aware project map path (if found) to the
  manifest map under the given mode-aware name"
  (let [value (get-in project path)
        transformed-value (f value)]
    (if (and (not (nil? value)) transformed-value)
      (update-in project (cc/capsule-manifest-path project mode-keyword)
                 #(merge
                   (or % {})
                   { manifest-entry-name transformed-value }))
      project)))

(defn ^:internal add-to-manifest-if-mode-path [project path manifest-entry-name f & [mode-keyword]]
  "Will add an entry's transformation through \"f\" in the given mode-aware project map path (if found) to the
  manifest map under the given mode-aware name"
  (add-to-manifest-if-path
    project
    (cc/mode-aware-path project path mode-keyword)
    manifest-entry-name
    f
    mode-keyword))

(defn ^:internal add-to-manifest-if-mode-path-as-string [project path manifest-entry-name & [mode-keyword]]
  "Will add an entry's toString() in the given mode-aware project map path (if found) to the manifest map under
  the given mode-aware name"
  (add-to-manifest-if-mode-path
    project
    path manifest-entry-name
    #(if (not (nil? %)) (let [val (.toString %)] (if (seq val) val)))
    mode-keyword))

(defn ^:internal add-to-manifest [project manifest-entry-name manifest-entry-value & [mode-keyword]]
  "Will add the specified entry to the manifest map"
  (if (seq manifest-entry-value)
    (update-in project
               (cc/capsule-manifest-path project mode-keyword)
               #(merge % { manifest-entry-name manifest-entry-value }))
    project))