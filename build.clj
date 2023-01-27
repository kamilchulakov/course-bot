(ns build
  (:require [clojure.tools.build.api :as b]))

(def build-folder "target")
(def jar-content (str build-folder "/classes"))

(def basis (b/create-basis {:project "deps.edn"}))
(def version "1.0.0")
(def app-name "course-bot")
(def uber-file-name (format "%s/%s-%s-standalone.jar" build-folder app-name version)) ; path for result uber file

(defn clean [_]
  (b/delete {:path "target"})
  (println (format "Build folder \"%s\" removed" build-folder)))

(defn uber [_]
  (clean nil)

  (b/copy-dir {:src-dirs   ["resources"]
               :target-dir jar-content})

  (b/compile-clj {:basis     basis
                  :src-dirs  ["src"]
                  :class-dir jar-content})

  (b/uber {:class-dir jar-content
           :uber-file uber-file-name
           :basis     basis
           :main      'course-bot.csa})

  (println (format "Uber file created: \"%s\"" uber-file-name)))