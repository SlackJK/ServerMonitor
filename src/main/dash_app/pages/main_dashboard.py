import dash
from dash import html, dcc, callback, Input, Output
import plotly.express as px
import plotly.graph_objs as go

dash.register_page(__name__)

fig = go.Figure(go.Indicator(
    mode="gauge+number",
    value=3605,
    domain={"x": [0, 1], "y": [0, 1]},
    title={"text": "Memory usage"},
    gauge={"threshold": {}}
))



layout = html.Div([
    html.H1("this is a temp test page"),
    html.P("this will be the main dashboard"),
    html.Div(
        dcc.Graph(figure=fig)
    )
])
