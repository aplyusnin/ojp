(defun isNeg (x)
    (if (< x 0) (true) (false)))

(defun isPos (x)
    (if (> x 0) true false))

(def List (range 10 -2))

(List)

(map isNeg List)

(reduce + List 0)

(def List1 (range 1 4))

(reduce * List1 5)