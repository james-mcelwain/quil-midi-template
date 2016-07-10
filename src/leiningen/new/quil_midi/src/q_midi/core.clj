(ns {{ns-name}}.core
  (:require
   [quil.core :as q]
   [clojure.core.async :refer [sliding-buffer chan go <!! >!! alt!! alts!! timeout]]
   [quil.middleware :as m]
   [{{ns-name}}.midi :as midi]))

(declare setup update-state draw-state m255)

;; Initialize the listener
(def midi-chan (midi/listener "virtual-midi"))

(defn setup []
  (q/frame-rate 30)
  {:cc0 0 :cc1 0})

(defn update-state [state]
  ; (println state)
  (midi/get-state-vals state))

(defn draw-state [state]
  (q/background (m255 (:cc0 state)) 0 (m255 (:cc0 state))))

(q/defsketch {{ns-name}}
  :features [:keep-on-top :exit-on-close]
  :size [800 800]
  :middleware [m/fun-mode]
  :setup setup
  :update update-state
  :draw draw-state)

;; util
(defn m255 [x]
  (/ (* x 255) 127))

;; main
(defn -main [] (print "loaded"))
