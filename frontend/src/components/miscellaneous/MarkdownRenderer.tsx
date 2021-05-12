/* eslint-disable react/display-name */
import { createElement, useMemo, ReactNode } from 'react';
import ReactMarkdown from 'react-markdown';

// Material UI
import { makeStyles } from '@material-ui/core/styles';
import Typography from '@material-ui/core/Typography';
import Divider from '@material-ui/core/Divider';

const useStyles = makeStyles((theme) => ({
  blockquote: {
    margin: theme.spacing(0, 2, 1),
    padding: theme.spacing(2, 3, 1),
    borderLeft: `${theme.spacing(1)}px solid ${theme.palette.primary.main}`,
  },
  code: {
    color: theme.palette.text.primary,
    background: theme.palette.action.selected,
    borderRadius: theme.shape.borderRadius,
    padding: theme.spacing(2),
    overflowX: 'auto',
  },
  divider: {
    margin: theme.spacing(1, 0),
  },
  inlineCode: {
    padding: theme.spacing(0.5, 1),
    color: theme.palette.text.primary,
    borderRadius: theme.shape.borderRadius,
    background: theme.palette.action.selected,
  },
  list: {
    listStylePosition: 'inside',
    marginLeft: theme.spacing(1),
  },
  listItem: {
    fontSize: theme.typography.body1.fontSize,
  },
  content: {
    marginBottom: theme.spacing(1),
    color: theme.palette.text.primary,
    overflowWrap: 'anywhere',
  },
  image: {
    maxWidth: '100%',
    objectFit: 'contain',
    height: 'auto',
    borderRadius: theme.shape.borderRadius,
  },
}));

export type MarkdownRendererProps = {
  value: string;
};

const MarkdownRenderer = ({ value }: MarkdownRendererProps) => {
  const classes = useStyles();

  const renderers = useMemo(
    () => ({
      blockquote: ({ children }: { children: ReactNode[] }) => createElement('blockquote', { className: classes.blockquote }, children),
      code: ({ value }: { value: string }) => createElement('pre', { className: classes.code }, createElement('code', {}, value)),
      heading: ({ level, children }: { level: number; children: ReactNode[] }) =>
        createElement(Typography, { variant: level === 1 ? 'h2' : 'h3', className: classes.content }, children),
      inlineCode: ({ value }: { value: string }) => createElement('code', { className: classes.inlineCode }, value),
      list: ({ children, ordered }: { children: ReactNode[]; ordered: boolean }) => createElement(ordered ? 'ol' : 'ul', { className: classes.list }, children),
      listItem: ({ children, checked }: { children: ReactNode[]; checked: boolean }) =>
        createElement('li', { className: classes.listItem }, checked ? createElement('input', { type: 'checkbox', checked, readOnly: true }) : null, children),
      paragraph: ({ children }: { children: ReactNode[] }) => createElement(Typography, { variant: 'body1', className: classes.content }, children),
      thematicBreak: () => <Divider className={classes.divider} />,
      image: ({ alt, src }: { alt: string; src: string }) => <img alt={alt} className={classes.image} src={src} />,
    }),
    [classes],
  );

  return <ReactMarkdown renderers={renderers}>{value}</ReactMarkdown>;
};

export default MarkdownRenderer;
