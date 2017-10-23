(ns understanding-re-frame.runner
    (:require [doo.runner :refer-macros [doo-tests]]
              [understanding-re-frame.core-test]))

(doo-tests 'understanding-re-frame.core-test)
