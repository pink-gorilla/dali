(ns dali.viewer.exception
  (:require
   [reagent.core :as r]))

(defn stacktrace-line [idx {:keys [class method file line]}]
  (let [base-cell-style {:padding "8px 10px"
                         :fontFamily "ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, monospace"
                         :fontSize "13px"
                         :lineHeight "1.35"
                         :verticalAlign "top"}
        row-style {:borderTop "1px solid #e5e7eb"}]
    ^{:key idx}
    [:tr {:style row-style}
     [:<>
      [:td {:style (merge base-cell-style {:color "#1e3a8a" :fontWeight 600})} class]
      [:td {:style (merge base-cell-style {:color "#1e3a8a"})} method]
      [:td {:style (merge base-cell-style {:color "#1e3a8a"})} (str file ": " line)]]]))

(defn exception-viewer [{:keys [message data class stacktrace] :as _ex}]
  (r/with-let [show-stacktrace (r/atom false)]
    (let [container-style {:background "linear-gradient(135deg, #fff5f5 0%, #ffe4e6 100%)"
                           :border "1px solid #fda4af"
                           :borderRadius "12px"
                           :padding "16px"
                           :color "#7f1d1d"
                           :boxShadow "0 8px 24px rgba(136, 19, 55, 0.12)"
                           :margin "10px 0"
                           :maxWidth "60em"
                           :width "100%"}
          title-style {:fontSize "16px"
                       :fontWeight 700
                       :margin "0 0 8px 0"}
          message-style {:margin "0 0 10px 0"
                         :fontSize "14px"}
          class-style {:margin "0 0 12px 0"
                       :fontFamily "ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, monospace"
                       :fontSize "13px"
                       :color "#9f1239"}
          button-style {:display "inline-block"
                        :border "1px solid #fb7185"
                        :background "#fff1f2"
                        :color "#9f1239"
                        :padding "6px 10px"
                        :borderRadius "8px"
                        :fontSize "13px"
                        :fontWeight 600
                        :cursor "pointer"}
          data-style {:margin "0 0 12px 0"
                      :padding "10px"
                      :background "#fff"
                      :border "1px solid #fecdd3"
                      :borderRadius "8px"
                      :fontFamily "ui-monospace, SFMono-Regular, Menlo, Monaco, Consolas, monospace"
                      :fontSize "12px"
                      :whiteSpace "pre-wrap"
                      :wordBreak "break-word"}
          table-style {:width "100%"
                       :marginTop "12px"
                       :borderCollapse "collapse"
                       :background "#ffffff"
                       :border "1px solid #fecdd3"
                       :borderRadius "8px"
                       :overflow "hidden"}]
      [:div {:style container-style}
       [:p {:style title-style} "Exception"]
       [:p {:style message-style} (or message "Unknown error")]
       [:p {:style class-style} (or class "Unknown class")]
       (when data
         [:pre {:style data-style}
          (pr-str data)])
       (when (seq stacktrace)
         [:div
          [:button {:type "button"
                    :style button-style
                    :on-click #(swap! show-stacktrace not)}
           (if @show-stacktrace "Hide stacktrace" "Show stacktrace")]
          (when @show-stacktrace
            [:table {:style table-style}
             [:tbody
              (map-indexed stacktrace-line stacktrace)]])])])))