(ns ^:figwheel-no-load transitions-example.dev
  (:require
    [transitions-example.core :as core]
    [devtools.core :as devtools]))


(enable-console-print!)

(devtools/install!)

(core/init!)
