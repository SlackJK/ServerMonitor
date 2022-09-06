import dash
from dash import html, dcc, callback, Input, Output
import dash_bootstrap_components as dbc
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
    html.Div([
        dbc.Row(dbc.Col(html.Div("row 1"))),
        dbc.Row(
            [
                dbc.Col(html.Div(dcc.Graph(figure=fig))),
                dbc.Col(html.Div(dcc.Graph(figure=fig))),
                dbc.Col(html.Div(dcc.Graph(figure=fig))),
            ]
        )
    ])
# html.Div(
#     [
#         dbc.Row(dbc.Col(html.Div("A single column"))),
#         dbc.Row(
#             [
#                 dbc.Col(html.Div("One of three columns")),
#                 dbc.Col(html.Div("One of three columns")),
#                 dbc.Col(html.Div("One of three columns")),
#             ]
#         ),
#     ])
])

