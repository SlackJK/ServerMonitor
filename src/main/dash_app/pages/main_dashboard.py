import dash
from dash import html, dcc, callback, Input, Output

dash.register_page(__name__)

layout = html.Div([
    html.H1("this is a temp test page"),
    html.P("this will be the main dashboard")
])
