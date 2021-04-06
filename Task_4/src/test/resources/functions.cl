(decl f (x acc))

(defun fact (x) (f x 1))

(defun f (0 acc) acc
         (x acc) (f (- x 1) (* x acc)))

(defun sum3 (a b c) (+ a (+ b c)))

(fact (sum3 1 2 3))

