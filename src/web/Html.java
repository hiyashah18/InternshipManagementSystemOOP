package web;

import model.ApplicationStatus;
import model.User;

/** Small helper that builds the HTML shared by every page (layout, styles, escaping). */
public final class Html {

    private Html() { }

    /** Escapes text so user input can never break the page (prevents HTML injection). */
    public static String esc(String s) {
        if (s == null) {
            return "";
        }
        return s.replace("&", "&amp;").replace("<", "&lt;").replace(">", "&gt;")
                .replace("\"", "&quot;").replace("'", "&#39;");
    }

    public static String badge(ApplicationStatus status) {
        return "<span class='badge " + status.name().toLowerCase() + "'>" + status.name() + "</span>";
    }

    public static String stat(String label, int value) {
        return "<div class='stat'><div class='num'>" + value + "</div><div>" + esc(label) + "</div></div>";
    }

    public static String page(String title, User user, String msg, String err, String body) {
        StringBuilder sb = new StringBuilder();
        sb.append("<!DOCTYPE html><html lang='en'><head><meta charset='UTF-8'>")
          .append("<meta name='viewport' content='width=device-width, initial-scale=1'>")
          .append("<title>").append(esc(title)).append(" - Internship Management System</title>")
          .append("<style>").append(CSS).append("</style></head><body>");

        sb.append("<nav><span class='brand'>Internship Management System</span><span class='links'>");
        if (user != null) {
            // POLYMORPHISM: each user type supplies its own menu
            for (String[] link : user.getMenuLinks()) {
                sb.append("<a href='").append(link[1]).append("'>").append(esc(link[0])).append("</a>");
            }
            sb.append("<a href='/logout' class='logout'>Logout</a>");
        } else {
            sb.append("<a href='/login'>Login</a><a href='/register'>Register</a>");
        }
        sb.append("</span></nav><main>");

        if (user != null) {
            sb.append("<div class='role'>").append(esc(user.getDashboardTitle())).append("</div>");
        }
        if (msg != null && !msg.isEmpty()) {
            sb.append("<div class='flash ok'>").append(esc(msg)).append("</div>");
        }
        if (err != null && !err.isEmpty()) {
            sb.append("<div class='flash bad'>").append(esc(err)).append("</div>");
        }
        sb.append(body).append("</main><footer>Group 6 &middot; Java OOP Mini Project</footer></body></html>");
        return sb.toString();
    }

    private static final String CSS =
        "*{box-sizing:border-box}body{margin:0;font-family:Segoe UI,Roboto,Arial,sans-serif;background:#f3f5fa;color:#1f2937}"
        + "nav{background:#1e3a8a;color:#fff;display:flex;justify-content:space-between;align-items:center;padding:14px 28px;flex-wrap:wrap}"
        + ".brand{font-weight:700;font-size:18px}.links a{color:#fff;text-decoration:none;margin-left:20px;opacity:.9}"
        + ".links a:hover{opacity:1;text-decoration:underline}.logout{background:#ef4444;padding:6px 12px;border-radius:6px}"
        + "main{max-width:1000px;margin:24px auto;padding:0 16px}h1{margin:6px 0 4px}h2{margin-top:28px}"
        + ".role{display:inline-block;background:#dbeafe;color:#1e40af;padding:4px 12px;border-radius:999px;font-size:13px;font-weight:600;margin-bottom:8px}"
        + ".card{background:#fff;border-radius:10px;padding:18px 20px;margin:14px 0;box-shadow:0 1px 4px rgba(0,0,0,.08)}"
        + ".card h3{margin:0 0 4px}.muted{color:#6b7280;font-size:14px}"
        + ".grid{display:grid;grid-template-columns:repeat(auto-fit,minmax(180px,1fr));gap:14px;margin:16px 0}"
        + ".stat{background:#fff;border-radius:10px;padding:18px;text-align:center;box-shadow:0 1px 4px rgba(0,0,0,.08)}"
        + ".stat .num{font-size:32px;font-weight:700;color:#1e3a8a}"
        + "label{display:block;margin:12px 0 4px;font-weight:600;font-size:14px}"
        + "input,select,textarea{width:100%;padding:10px;border:1px solid #cbd5e1;border-radius:6px;font-size:15px;font-family:inherit}"
        + ".btn,button{background:#2563eb;color:#fff;border:0;padding:9px 16px;border-radius:6px;cursor:pointer;font-size:14px;text-decoration:none;display:inline-block}"
        + "button:hover,.btn:hover{background:#1d4ed8}.btn.red,button.red{background:#dc2626}.btn.green,button.green{background:#16a34a}"
        + ".btn.grey,button.grey{background:#6b7280}form.inline{display:inline}"
        + ".row{display:flex;gap:10px;flex-wrap:wrap;align-items:end}.row>div{flex:1;min-width:160px}"
        + "table{width:100%;border-collapse:collapse;background:#fff;border-radius:10px;overflow:hidden;box-shadow:0 1px 4px rgba(0,0,0,.08);margin:12px 0}"
        + "th,td{padding:10px 12px;text-align:left;border-bottom:1px solid #e5e7eb;font-size:14px;vertical-align:top}th{background:#eef2ff}"
        + ".badge{padding:3px 10px;border-radius:999px;font-size:12px;font-weight:700}"
        + ".badge.pending{background:#fef3c7;color:#92400e}.badge.accepted{background:#dcfce7;color:#166534}"
        + ".badge.rejected{background:#fee2e2;color:#991b1b}.badge.open{background:#dcfce7;color:#166534}.badge.closed{background:#e5e7eb;color:#374151}"
        + ".flash{padding:12px 16px;border-radius:8px;margin:12px 0}.flash.ok{background:#dcfce7;color:#166534}.flash.bad{background:#fee2e2;color:#991b1b}"
        + ".auth{max-width:440px;margin:30px auto}footer{text-align:center;color:#9ca3af;font-size:13px;padding:30px}";
}
