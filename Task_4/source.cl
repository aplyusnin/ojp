(def x 10)

(decl f (x acc))

(defun fact (x) (f x 1))

(defun f (0 acc) acc
         (x acc) (f (- x 1) (* acc x)))

(fact 5)

(defun sum-square (a b)
       (let ((a2 (* a a))
             (b2 (* b b))
             (ab (* a b))
             (ab2 (* ab 2)))
       (+ a2 (+ b2 ab2)))
)

(sum-square 2 2)

(defun do-fact (x)
    (do (print "Calculate factorial ")
        (print x)
        (print " ")
        (fact x)))

(do-fact 3)

(def List (range 1 5))

(map do-fact List)


(decl is-odd (x))

(decl is-even (x))

(defun is-even (0) true
               (1) false
               (x) (not (is-odd (- x 1))))


(defun is-odd (0) true
              (1) false
              (x) (not (is-even (- x 1))))

(map is-even List)

(defun is-good (x)
       (if (or (= x 3) (< x 2)) true false))

(map is-good List)

((reduce + List 0))

(defun acos (x) (@java.lang.Math/acos x))

(acos -1.0)
