(ns demo.person)

(defn person [{:keys [first-name last-name]}]
  [:p "FirstName: " first-name " LastName: " last-name])