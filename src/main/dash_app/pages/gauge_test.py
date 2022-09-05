import dash
from dash import html, dcc, callback, Input, Output
import plotly.express as px
import plotly.graph_objs as go
import plotly.io as pio

dash.register_page(__name__)

# TODO fix bs figure that doesnt work
fig = {
    "values": [4000, 2000, 1000, 1000],
    "marker": {
        "line": {
            "width": 2,
            "color": "black"
        },
    },
    "hole": .75,
    "type": "pie",
    "direction": "clockwise",
    "sort": False,
    "showlegend": False,
}


layout = html.Div([
    html.H1("this is a temp test page"),
    html.P("custom gauge test"),
    html.Div(
        dcc.Graph(figure=fig)
    )
])
