(ns {{ns-name}}.midi
  (:require [overtone.midi :as midi]
            [clojure.core.match :refer [match]]
            [clojure.core.async :refer [go go-loop chan sliding-buffer >! >!! alts!!  <! <!!]]))

(declare cc0 cc1 iac-device-name dispatch listener try-val get-state-vals)

(def cc0 (chan (sliding-buffer 1)))
(def cc1 (chan (sliding-buffer 1)))

(defn dispatch
  [midi-msg]
  "Dispatches a midi message to the appropriate channel"
  (let [{n :note
         v :velocity} midi-msg]
    (match n
           0 (>!! cc0 v)
           1 (>!! cc1 v))))

(defn listener [device-name]
  "Returns a device that can be closed"
  (let [device (midi/midi-in device-name)]
    (midi/midi-handle-events device dispatch)
    (:device device)))

(defn try-val
  [chan default]
  "Tries to get a value from a midi channel or returns the default"
  (first (alts!! [chan] :default default)))

(defn get-state-vals
  [state]
  "Updates state from quil draw func"
  {:cc0 (try-val cc0 (:cc0 state))
   :cc1 (try-val cc1 (:cc1 state))})
