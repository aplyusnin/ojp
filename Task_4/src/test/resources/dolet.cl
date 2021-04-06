(defun square-sum (a b)
    (let ((a2 (* a a))
          (b2 (* b b))
          (ab (* a b))
          (ab2 (* 2 ab)))
          ((+ (+ a2 b2) ab2))))

(square-sum 2 3)

(defun square-sum-write (a b)
    (do
        (print "(")
        (print a)
        (print "+")
        (print b)
        (print ")^2=")
        (square-sum a b)))

(square-sum-write 1 5)