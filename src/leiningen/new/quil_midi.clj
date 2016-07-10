(ns leiningen.new.quil-midi
  (:use [leiningen.new.templates :only [renderer name-to-path sanitize-ns ->files]]))

(def render (renderer "quil-midi"))

(defn quil-midi
  [name]
  (let [data {:name name
              :ns-name (sanitize-ns name)
              :sanitized (name-to-path name)}]
    (->files data ["test/{{sanitized}}/core_test.clj" (render "test/q_midi/core_test.clj" data)]
["src/{{sanitized}}/core.clj" (render "src/q_midi/core.clj" data)]
["src/{{sanitized}}/midi.clj" (render "src/q_midi/midi.clj" data)]
["project.clj" (render "project.clj" data)]
)))